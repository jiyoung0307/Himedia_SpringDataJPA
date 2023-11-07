package com.ohgiraffers.datajpa.menu.controller;

import com.ohgiraffers.datajpa.common.paging.Pagenation;
import com.ohgiraffers.datajpa.common.paging.SelectCriteria;
import com.ohgiraffers.datajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.datajpa.menu.dto.MenuDTO;
import com.ohgiraffers.datajpa.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	private final MenuService menuService;
	
	@Autowired
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping("/{menuCode}")
	public ModelAndView findMenuByCode(ModelAndView mv, @PathVariable int menuCode) {

		MenuDTO menu = menuService.findMenuByCode(menuCode);
		
		mv.addObject("menu", menu);
		mv.setViewName("/menu/one");
		
		return mv;
	}
	
	@GetMapping("/list")
	public ModelAndView findMenuList(ModelAndView mv) {

		List<MenuDTO> menuList = menuService.findMenuList();
		
		mv.addObject("menuList", menuList);
		mv.setViewName("menu/list");
		
		return mv;
	}
	
	@GetMapping("/regist")
	public void registPage() {}
	
	@GetMapping(value="/category", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<CategoryDTO> findCategoryList(){

		return menuService.findAllCategory();
	}
	
	@PostMapping("/regist")
	public ModelAndView registMenu(ModelAndView mv, MenuDTO newMenu, RedirectAttributes rttr) {

		menuService.registNewMenu(newMenu);

		rttr.addFlashAttribute("registSuccessMessage", "메뉴 등록에 성공하셨습니다");
		mv.setViewName("redirect:/menu/list");
		
		return mv;
	}
	
	@GetMapping("/modify")
	public void modifyPage() {}
	
	@PostMapping("/modify")
	public String modifyPage(RedirectAttributes rttr, @ModelAttribute MenuDTO menu) {
		
		menuService.modifyMenu(menu);
		
		rttr.addFlashAttribute("modifySuccessMessage", "메뉴 수정에 성공하셨습니다");

		return "redirect:/menu/" + menu.getMenuCode();
	}

	@GetMapping("/search")
	public ModelAndView searchPage(HttpServletRequest request, ModelAndView mv) {

		String currentPage = request.getParameter("currentPage");
		int pageNo = 1;

		if(currentPage != null && !"".equals(currentPage)) {
			pageNo = Integer.parseInt(currentPage);
		}

		String searchCondition = request.getParameter("searchCondition");
		String searchValue = request.getParameter("searchValue");

		int totalCount = menuService.selectTotalCount(searchCondition, searchValue);

		/* 한 페이지에 보여 줄 게시물 수 */
		int limit = 10;		// 얘도 파라미터로 전달받아도 된다.

		/* 한 번에 보여질 페이징 버튼의 갯수 */
		int buttonAmount = 5;

		/* 페이징 처리를 위한 로직 호출 후 페이징 처리에 관한 정보를 담고 있는 인스턴스를 반환받는다. */
		SelectCriteria selectCriteria = null;
		if(searchValue != null && !"".equals(searchValue)) {
			selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
		} else {
			selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
		}
		System.out.println(selectCriteria);

		List<MenuDTO> menuList = menuService.searchMenuList(selectCriteria);

		for(MenuDTO menu : menuList) {
			System.out.println(menu);
		}

		mv.addObject("menuList", menuList);
		mv.addObject("selectCriteria", selectCriteria);
		mv.setViewName("menu/search");

		return mv;
	}
}



//package testProject;
//
//import java.sql.Date;
//
//public class TestDate {
//
//   public static void main(String[] args) {
//      /* db에서 가져온 시작 및 종료 sql.Date형 */
//      Date endDate = new Date(2022, 7-1, 20);      // appVac2.getVacEndDate()
//      Date startDate = new Date(2022, 7-1, 15);  // appVac2.getVacStartDate()
//      
////      System.out.println("종료일 long: " + endDate.getTime());
////      System.out.println("시작일 long: " + startDate.getTime());
////      
////      System.out.println("날짜차이 long: " + (endDate.getTime() - startDate.getTime())/1000/60/60/24);
//      
//      /* 년월일만 뽑아서(시분초 무시되게) 다시 sql.Date형으로 만들고 long형 값으로 만들기 */
////      System.out.println("endDate 년 : " + endDate.getYear());
////      System.out.println("endDate 월 : " + (endDate.getMonth() + 1));
////      System.out.println("endDate 일 : " + endDate.getDate());
//      
//      // endDate 뽑아낸 년월일로 long형 만들기
//      long newEndDate = new Date(endDate.getYear(), endDate.getMonth(), endDate.getDate()).getTime();
//      
//      // startDate 뽑아낸 년월일로 long형 만들기
//      long newstartDate = new Date(startDate.getYear(), startDate.getMonth(), startDate.getDate()).getTime();
//      
//      System.out.println((newEndDate - newstartDate)/1000/60/60/24 + "일");
//   }
//
//}