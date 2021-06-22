package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping(value = "/board/list")
	public JsonObject openBoardList() {

		JsonObject jsonObj = new JsonObject();

		List<BoardDTO> boardList = boardService.getBoardList();
		if (CollectionUtils.isEmpty(boardList) == false) {
			JsonArray jsonArr = new Gson().toJsonTree(boardList).getAsJsonArray();
			jsonObj.addProperty("message", "Success To Show List");
			jsonObj.add("data", jsonArr);
		}

		return jsonObj;
	}

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
	}

	@PostMapping(value = "/board/delete")
	public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx) {
		if (idx == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/board/list.do";
		}

		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if (isDeleted == false) {
				// TODO => 게시글 삭제에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/board/list.do";
	}

}