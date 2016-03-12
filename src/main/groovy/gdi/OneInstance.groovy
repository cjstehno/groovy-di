package gdi

import groovy.transform.AnnotationCollector
import groovy.transform.Memoized

/**
 * An alias to the configured memoized annotation.
 */
@Memoized(protectedCacheSize = 1, maxCacheSize = 1)
@AnnotationCollector
@interface OneInstance {}
