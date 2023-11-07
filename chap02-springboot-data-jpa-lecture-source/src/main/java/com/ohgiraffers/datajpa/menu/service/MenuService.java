package com.ohgiraffers.datajpa.menu.service;

import com.ohgiraffers.datajpa.common.paging.SelectCriteria;
import com.ohgiraffers.datajpa.menu.entity.Category;
import com.ohgiraffers.datajpa.menu.entity.Menu;
import com.ohgiraffers.datajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.datajpa.menu.dto.MenuDTO;
import com.ohgiraffers.datajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.datajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

	private final MenuRepository menuRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;			// modelMapper 빈을 선언
	
	@Autowired
	public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.menuRepository = menuRepository;
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	public MenuDTO findMenuByCode(int menuCode) {

		/* findById메소드로 Optional 객체 조회후 Optional객체의 get메소드를 통해 조회 */
		Menu menu = menuRepository.findById(menuCode).get();
		
		/* ModelMapper를 이용하여 entity를 DTO로 변환 후 MenuDTO로 반환 */
		return modelMapper.map(menu, MenuDTO.class);
	}

	public List<MenuDTO> findMenuList() {

		List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode"));					// Sort는 org.springframework.data.domain패키지로 import
//		List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());		// 내림차순을 하고 싶다면 뒤에 .descending()을 작성하면 된다
		
		/* ModelMapper를 이용하여 entity를 DTO로 변환 후 List<MenuDTO>로 반환 */
		return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
	}

	public List<CategoryDTO> findAllCategory() {

		List<Category> categoryList = categoryRepository.findAllCategory();
		
		/* ModelMapper를 이용하여 entity를 DTO로 변환 후 List<CategoryDTO>로 반환 */
		return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
	}

	@Transactional
	public void registNewMenu(MenuDTO newMenu) {

		menuRepository.save(modelMapper.map(newMenu, Menu.class));
	}

	@Transactional
	public void modifyMenu(MenuDTO menu) {

		Menu foundMenu = menuRepository.findById(menu.getMenuCode()).get();
		foundMenu.setMenuName(menu.getMenuName());
	}

	public int selectTotalCount(String searchCondition, String searchValue) {

		int count = 0;
		if(searchValue != null) {
			if("menuName".equals(searchCondition)) {
				count = menuRepository.countByMenuNameContaining(searchValue);
			}

			/* 가격 검색일 경우 */
			if("menuPrice".equals(searchCondition)) {
				count = menuRepository.countByMenuPriceLessThanEqual(Integer.valueOf(searchValue));
			}
		} else {
			count = (int)menuRepository.count();
		}

		return count;
	}
	
	// 필요한 페이징 부분 잘라서 쓰는 부분
	public List<MenuDTO> searchMenuList(SelectCriteria selectCriteria) {

		int index = selectCriteria.getPageNo() - 1;			// Pageble객체를 사용시 페이지는 0부터 시작(1페이지가 0)
		int count = selectCriteria.getLimit();
		String searchValue = selectCriteria.getSearchValue();

		/* 페이징 처리와 정렬을 위한 객체 생성 */
		Pageable paging = PageRequest.of(index, count, Sort.by("menuCode"));	// Pageable은 org.springframework.data.domain패키지로 import

		List<Menu> menuList = new ArrayList<Menu>();
		if(searchValue != null) {

			/* category 검색일 경우 */
			if("menuName".equals(selectCriteria.getSearchCondition())) {
				menuList = menuRepository.findByMenuNameContaining(selectCriteria.getSearchValue(), paging);
			}

			/* 가격 검색일 경우 */
			if("menuPrice".equals(selectCriteria.getSearchCondition())) {
				menuList = menuRepository.findByMenuPriceLessThanEqual(Integer.valueOf(selectCriteria.getSearchValue()), paging);
			}
		} else {
			menuList = menuRepository.findAll(paging).toList();
		}

		/* 자바의 Stream API와 ModelMapper를 이용하여 entity를 DTO로 변환 후 List<MenuDTO>로 반환 */
		return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
	}
}
