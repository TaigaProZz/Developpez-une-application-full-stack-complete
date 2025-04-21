package com.openclassrooms.mddapi.theme.repository;

import com.openclassrooms.mddapi.theme.model.Theme;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long> {
    @Modifying
    @Query(value = "INSERT INTO user_themes (user_id, theme_id) VALUES (:userId, :themeId)", nativeQuery = true)
    void addUserToTheme(@Param("userId") Long userId, @Param("themeId") Long themeId);

    @Query("SELECT COUNT(ut) > 0 FROM User u JOIN u.themes ut WHERE u.id = :userId AND ut.id = :themeId")
    boolean isUserSubscribedToTheme(@Param("userId") Long userId, @Param("themeId") Long themeId);

    @Query("SELECT t FROM Theme t JOIN t.users u WHERE u.id = :userId")
    List<Theme> findSubscribedThemesByUserId(@Param("userId") Long userId);
}
