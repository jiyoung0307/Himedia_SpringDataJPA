package com.ohgiraffers.jpa.menu.controller;

import java.util.List;
import java.util.Locale;

import com.ohgiraffers.jpa.menu.dto.CategoryDTO;
import com.ohgiraffers.jpa.menu.dto.MenuDTO;
import com.ohgiraffers.jpa.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	private MenuService menuService;
	
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
	
	@GetMapping("regist")
	public void registPage() {}
	
	@GetMapping(value="category", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<CategoryDTO> findCategoryList(){

		return menuService.findAllCategory();
	}
	
	@PostMapping("regist")
	public ModelAndView registMenu(ModelAndView mv, MenuDTO newMenu, RedirectAttributes rttr, Locale locale) {
		System.out.println(newMenu);
		menuService.registNewMenu(newMenu);

		rttr.addFlashAttribute("registSuccessMessage", "메뉴 등록에 성공하셨습니다");
		mv.setViewName("redirect:/menu/list");
		
		return mv;
	}
	
	@GetMapping("modify")
	public void modifyPage() {}
	
	@PostMapping("modify")
	public String modifyPage(RedirectAttributes rttr, @ModelAttribute MenuDTO menu) {
		
		menuService.modifyMenu(menu);
		
		rttr.addFlashAttribute("modifySuccessMessage", "메뉴 수정에 성공하셨습니다");

		return "redirect:/menu/" + menu.getMenuCode();
	}
	
	
}
