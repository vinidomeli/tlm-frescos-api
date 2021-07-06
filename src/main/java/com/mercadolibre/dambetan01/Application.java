package com.mercadolibre.dambetan01;

import com.mercadolibre.dambetan01.config.SpringConfig;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.Supervisor;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.*;
import com.mercadolibre.dambetan01.util.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.UUID;

@SpringBootApplication
public class Application {

	static UserRepository userRepository;
	static SellerRepository sellerRepository;
	static SupervisorRepository supervisorRepository;
	static ProductRepository productRepository;
	static WarehouseRepository warehouseRepository;
	static SectionRepository sectionRepository;

	public Application(UserRepository userRepository, SellerRepository sellerRepository,
					   SupervisorRepository supervisorRepository, ProductRepository productRepository,
					   WarehouseRepository warehouseRepository, SectionRepository sectionRepository) {
		Application.userRepository = userRepository;
		Application.sellerRepository = sellerRepository;
		Application.supervisorRepository = supervisorRepository;
		Application.productRepository = productRepository;
		Application.warehouseRepository = warehouseRepository;
		Application.sectionRepository = sectionRepository;
	}

	public static void main(String[] args) {
		ScopeUtils.calculateScopeSuffix();
		new SpringApplicationBuilder(SpringConfig.class).registerShutdownHook(true)
				.run(args);

		UserDB userDB = new UserDB(userRepository, sellerRepository, supervisorRepository);
		ProductDB productDB = new ProductDB(sellerRepository, productRepository);
		SectionDB sectionDB = new SectionDB(warehouseRepository, sectionRepository);
		WarehouseDB warehouseDB = new WarehouseDB(warehouseRepository, supervisorRepository);

		for(int i = 0; i < 100; i++) {
			Long registerNumber = userDB.insertSupervisor(userDB.insertUser(Roles.SUPERVISOR));
			String CNPJ = userDB.insertSeller(userDB.insertUser(Roles.SELLER));
			UUID warehouseCode = warehouseDB.insertWarehouse(registerNumber);
			productDB.insertProduct(CNPJ, ProductType.REFRIGERATE);
			sectionDB.insertSection(ProductType.REFRIGERATE, warehouseCode);
		}

	}
}
