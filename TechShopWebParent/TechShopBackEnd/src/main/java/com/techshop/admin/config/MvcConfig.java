package com.techshop.admin.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// user
		String dirName = "user-photos";
		Path userPhotoDir = Paths.get(dirName);
		String userPhotoPath = userPhotoDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotoPath + "/");

		// category

		String categoryImageDirName = "category-images";
		Path categoryImageDir = Paths.get(categoryImageDirName);
		String categoryImagePath = categoryImageDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + categoryImageDirName + "/**")
				.addResourceLocations("file:/" + categoryImagePath + "/");

		// brand
		String brandLogoDirName = "../brand-logos";
		Path brandLogoDir = Paths.get(brandLogoDirName);

		String brandLogoPath = brandLogoDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/brand-logos/**").addResourceLocations("file:/" + brandLogoPath + "/");
		
		//product
		String productLogoDirName = "../product-images";
		Path productLogoDir = Paths.get(productLogoDirName);

		String productLogoPath = productLogoDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/product-images/**").addResourceLocations("file:/" + productLogoPath + "/");
	}
}
