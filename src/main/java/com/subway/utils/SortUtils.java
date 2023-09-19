package com.subway.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtils {

    public Sort getSort(String sortOption, String sortDirection) {

        if(sortOption == null){
            return null;
        }

        Sort sortBySortOption = getSortBySortOption(sortOption);

        return setSortDirection(sortBySortOption, sortDirection);
    }

    private Sort setSortDirection(Sort sort, String sortDirection) {

        if (sortDirection==null){
            return sort;
        }

        if (sortDirection.trim().equalsIgnoreCase("DESC")) {
            return sort.reverse();
        }

        return sort;
    }

    private Sort getSortBySortOption(String sortOption) {

        if (sortOption.trim().equalsIgnoreCase("kcal")) {
            return Sort.by("kcal");
        }
        if (sortOption.trim().equalsIgnoreCase("protein")) {
            return Sort.by("protein");
        }
        if (sortOption.trim().equalsIgnoreCase("fat")) {
            return Sort.by("fat");
        }
        if (sortOption.trim().equalsIgnoreCase("price")) {
            return Sort.by("price");
        }

        return Sort.by("id");
    }

}
