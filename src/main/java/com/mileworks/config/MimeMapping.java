package com.mileworks.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

/**
 * 添加资源访问不了mime修改
 * @author long-laptop
 * 2017.12.5
 */
@Configuration
public class MimeMapping implements EmbeddedServletContainerCustomizer{

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("svg", "image/svg+xml");
        mappings.add("woff","application/font-woff");
        mappings.add("woff2","application/font-woff2");
        container.setMimeMappings(mappings);
	}

}
