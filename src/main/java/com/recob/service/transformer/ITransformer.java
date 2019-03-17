package com.recob.service.transformer;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * basic transformer interface
 * to create object T from F
 * @param <T> return object
 * @param <F> param object
 */

public interface ITransformer <T, F> {

    T transform(F f);

    default List<T> transformList(List<F> fs) {
        if (!CollectionUtils.isEmpty(fs)) {
            return fs.stream()
                    .map(this::transform)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

}
