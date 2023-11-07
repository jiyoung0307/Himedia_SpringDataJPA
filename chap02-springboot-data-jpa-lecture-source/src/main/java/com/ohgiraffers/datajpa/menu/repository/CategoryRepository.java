package com.ohgiraffers.datajpa.menu.repository;

import java.util.List;

import com.ohgiraffers.datajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/* MenuRepository를 작성하다 네이티브 쿼리 작성 시 추가할 인터페이스 */
public interface CategoryRepository extends JpaRepository<Category, Integer>{

    @Query(value = "SELECT A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE FROM TBL_CATEGORY A ORDER BY A.CATEGORY_CODE ASC", nativeQuery = true)
    public List<Category> findAllCategory();

}
