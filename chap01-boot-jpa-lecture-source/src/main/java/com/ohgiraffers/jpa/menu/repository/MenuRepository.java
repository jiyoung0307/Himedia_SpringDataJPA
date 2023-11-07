package com.ohgiraffers.jpa.menu.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ohgiraffers.jpa.menu.domain.Category;
import com.ohgiraffers.jpa.menu.domain.Menu;
import com.ohgiraffers.jpa.menu.dto.MenuDTO;
import org.springframework.stereotype.Repository;

@Repository
public class MenuRepository {
	
	@PersistenceContext	// 어노테이션이면 스프링부트가 (알아서)영속성 컨텍스트를 관리하는 엔티티 매니저를 가져온다.(템플릿 필요 X)
	private EntityManager entityManager;

	public Menu findMenuByCode(int menuCode) {

		return entityManager.find(Menu.class, menuCode);
	}
	
	public List<Menu> findAllMenu() {

		String jpql = "SELECT m FROM Menu as m ORDER BY m.menuCode ASC";
		
		return entityManager.createQuery(jpql, Menu.class).getResultList();
	}
	
	public List<Category> findAllCategory() {

		String jpql = "SELECT c FROM Category as c ORDER BY c.categoryCode ASC";
		
		return entityManager.createQuery(jpql, Category.class).getResultList();
	}
	
	public void registNewMenu(Menu menu){

		entityManager.persist(menu);
	}

	public void modifyMenu(Menu menu) {

		Menu selectedMenu = entityManager.find(Menu.class, menu.getMenuCode());
		selectedMenu.setMenuName(menu.getMenuName());	// 속성값을 setter로 변경
	}

}
