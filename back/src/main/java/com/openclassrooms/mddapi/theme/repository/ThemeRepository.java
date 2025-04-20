package com.openclassrooms.mddapi.theme.repository;

import com.openclassrooms.mddapi.theme.model.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long> {
}
