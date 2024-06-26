package com.toby.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

	private final ClassLoader classLoader;

	public MyAutoConfigImportSelector(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public String[] selectImports(final AnnotationMetadata importingClassMetadata) {
		//final Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
		//return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);
		final List<String> autoConfigs = new ArrayList<>();

		ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);

		return autoConfigs.toArray(new String[0]);
	}

}
