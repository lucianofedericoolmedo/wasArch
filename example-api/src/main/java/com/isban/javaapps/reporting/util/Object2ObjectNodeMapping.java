package com.isban.javaapps.reporting.util;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Object2ObjectNodeMapping {

	abstract ObjectNode map(Object[] object);

}
