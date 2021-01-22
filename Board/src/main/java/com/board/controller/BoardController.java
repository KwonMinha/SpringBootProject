package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.google.gson.JsonObject;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	/*
	@ResponseBody // JSON 객체 반환을 위함 
	@GetMapping(value = "/board/write") // URI 경로 
	public String openBoardWrite() {

//		// html로 반환하려면 매개변수에 Model model 추가 
//		String title = "제목";
//		String content = "내용";
//		String writer = "홍길동";
//
//
//		model.addAttribute("t", title);
//		model.addAttribute("c", content);
//		model.addAttribute("w", writer);
//
//		return "board/write"; // html로 반환 
		
		
		// JSON 객체로 반환 
		JsonObject obj =new JsonObject();

	    obj.addProperty("idx", "id");
	    obj.addProperty("title", "title");
	    obj.addProperty("content", "content");
	    obj.addProperty("writer", "writer");

	    return obj.toString();
	}
	*/
	
//	@GetMapping(value = "/board/write")
//	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
//		if (idx == null) {
//			model.addAttribute("board", new BoardDTO());
//		} else {
//			BoardDTO board = boardService.getBoardDetail(idx);
//			if (board == null) {
//				return "redirect:/board/list";
//			}
//			model.addAttribute("board", board);
//		}
//
//		return "board/write";
//	}
	
	@ResponseBody
	@PostMapping(value = "/board/register")
	public String registerBoard(final BoardDTO params) {
		JsonObject obj =new JsonObject();
		
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
				obj.addProperty("message", "Fail To Sign Up");
			    return obj.toString();
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달
			obj.addProperty("message", "DataAccessException");
		    return obj.toString();

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
			obj.addProperty("message", "Internal Server Error"); // 상태코드 500
		    return obj.toString();
		}

	    obj.addProperty("message", "Success To Sign Up");

	    return obj.toString();


		//return "redirect:/board/list";
	}
}