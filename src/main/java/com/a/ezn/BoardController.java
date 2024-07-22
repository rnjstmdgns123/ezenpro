package com.a.ezn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.a.ezn.repo.BoardRepository;

/* 컨트롤러는 데이터베이스 접근과, 실제 화면에 대한 접근을 처리해주는 코드만을 작성해야한다.
 * 데이터 베이스에 대한 접근은(CRUD) DAO(Repository) 클래스를 이용해서만 접근해야한다. 
 */



//스프링에서 관리하는 객체가 됨
//상위에 component 어노테이션이 있고 하위에 Controller가 있음
//servlet-context.xml이 읽히면서 component가 읽히고 그 하위인
//Controller가 스프링에 의해 관리됨

/*
 * BoardController 클래스가 com.a.ezn 패키지  아래에 존재하고
 * @Controller(@Component) 어노테이션이 달려있기 떄문에
 * 컴포넌트 스캔시 BOardController 클래스의 생성자를 스프링에서 호출하고 IOC 컨테이너에 넣는다. 
 */
@Controller
//특정 url 경로로 요청이 왔을 때 특정 클래스 안에서 메서드를 찾게된다.
// ~~~/board/로 시작하는 url로 요청이 오면 BoardController안에서 하위 url을 찾는다.
// ex) ~~/board/board.do => BoardCOntroller안에 board.do를 찾는다.

// /board로 url이 들어오면 해당 class가 실행됨
@RequestMapping("/board")
public class BoardController {
	
	//com.a.ezn.repo -> BoardRepository클래스와 연결 시켜줌
	//BoardRepository repository = new BoardRepository();
	
	/*
	 * @AUtowired 어노테이션이 달린 필드나, setter메서드나, 생성자는
	 * 해당하는 클래스를 IOC 컨테이너에서 찾아 주입(대입)한다.
	 * IOC컨테이너에 등록된 생성자의 타입을 이용해서 찾는다.
	 * 이 단계를 의존성주입(DI, Dependency Injection)
	 * BoardController 클래스는 BoardRepository 클래스에 의존하고 있는 것
	 */
	@Autowired
	BoardRepository repository;
	
	@Autowired
	JdbcTemplate template;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	ServletContext servletContext;
	
	//BoardVO board = new BoardVO(); ->@Autowired 와 같다.
	//그래서 아래에 new를 통해 객체를 생성하지 않아도 board.getTitle()로 접근이 가능하여 값을 가져온다.
	//board1이라는 name을 가진 boardVO를 가져옴
	//@Qualifier("board2")
	
	//url에 /board.do 메소드 GET으로 요청이 오면 해당 클래스 실행
	//인자 값으로 Model, BoardVo를 받아오는데
	
	/*
	 * 메서드에 @RequestMapping 어노테이션이 달려있다면 value 속성에 넣은 url로 요청이 올 때,
	 * method 속성에 맞는 http 요청이 올 때 해당 메서드를 실행한다.
	 * ex) get 요청으로 /board/board.do 요청이 온다면 아래의 board 메서드가 실행된다.
	 */
	@RequestMapping(value="/board.do", method=RequestMethod.GET)
	public String board(
			//model 객체는 서블릿에서 request.setAttribute를 이용해
			//특정 jsp로 포워딩 시 데이터를 넘겨주는것과 동일한 역할을 수행한다.
			//스프링이 board 메서드를 실행할 때, 메서드의 파라미터로 넣어준다.
			Model model,BoardVO vo,
			
			//request.getParameter()와 동일한 역할을 수행한다.
			//url 파라미터에서 name 속성의 값을 꺼내오는 것.
			//name속성의 값은 url로 요청이 오는 값 ex)?searchType=
			//required속성은 url 파라미터로 반드시 요청이 와야하는지에 대한 여부
			@RequestParam(name="searchType", required=false) String searchType,
			@RequestParam(name="keyword", required=false) String keyword,
			//defaultValue 속성은 url 파라미터로 요청이 오지 않았을 때에 기본값을 넣어준다.
			@RequestParam(name="page", required=false, defaultValue="1") int page) {
		
		Pageable pageable = PageRequest.of(page - 1, 10); 
		
		Page<BoardVO> data = repository.getAllDate(pageable, searchType, keyword);
		
		data.getTotalPages();
		
		//특정 jsp페이지로 포워딩 될 때 데이터를 넣어준다.
		//데이터는 키-벨류 형식으로 넣어주어야 한다.
		//아래는 "vo"라는 키에 게시글 데이터를 넣어준것
		model.addAttribute("vo",data.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", data.getTotalPages());
		model.addAttribute("pageSize", 10);
		
		//@RequestMapping 어노테이션이 달린 메서드가 문자열로 데이터를 반환한다면
		//뷰 리졸버가 이를 가로채, 특정 jsp로 포워딩 시킨다.
		// -> WEB-INF/view/board.jsp로 포워딩 된다.
		return "board";
		
		
	}
	
	
	@RequestMapping(value="/post.do", method=RequestMethod.GET)
	public String view(@RequestParam(name="sno",defaultValue="0") int sno, Model model) {
		
		BoardVO vo = repository.selectOne(sno);
		model.addAttribute("vo", vo);
		
		return "view1";
	}

	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write(HttpSession session) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
			return "redirect:/user/login.do";
		}
		return "write";
	}
	
	
	
	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String writeOk(
			//url로 요청온 파라미터가 특정 클래스 내에 존재하는 필드와 동일하다면
			//클래스 타입으로 url 파라미터를 받을 수 있다.
			//ex) ?sno=2&title=제목&body=본문 ...과 같이 요청이 온다면
			//vo클래스에 해당 값을 setter 메서드를 이용해 호출하고 담아준다.
			BoardVO vo, 
			//여러개의 첨부파일을 받기 위해서는 MultipartFile 설정을 한 후
			//컨트롤러 메서드의 파라미터로 첨부파일을 받아올 수 있다.
			@RequestParam("file")MultipartFile[] files) {
		
		repository.insertOne(vo);
		int result = vo.getSno();
		
		String uploadDir = servletContext.getRealPath("/upload/");
		File dir = new File(uploadDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		List<FileVO> fileList = new ArrayList<>(); 
				
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				String originFileName = file.getOriginalFilename();
				String uniqueFileName = UUID.randomUUID().toString() + "." + getFileExtension(originFileName);
				String filepath = uploadDir + uniqueFileName;
				//originFileName = a.txt
				//uniqueFileName = asdasdasdsd.txt
				//filePath = uploads/asdasdasdsd.txt
				try {
					file.transferTo(new File(filepath));
					FileVO fileVo = new FileVO();
					
					fileVo.setSno(result);
					fileVo.setFileName(originFileName);
					fileVo.setFilePath(filepath);
					fileVo.setFileSize(file.getSize() + "");
					fileVo.setFileType(file.getContentType());
					
					fileList.add(fileVo);
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		if(result >= 1) {
			if(fileList.size() != 0 ) {
				fileRepository.insert(fileList);
			}
			return "redirect:/board/post.do?sno=" + vo.getSno();
		}else {
			return "redirect:/board/board.do";
		}
		
		
	}
	
	
	
	@RequestMapping(value="/modify.do", method=RequestMethod.GET)
	public String modifyBoard(@RequestParam(name="sno", required=false, defaultValue="0") int sno, Model model) {
		
		BoardVO vo = repository.selectOne(sno);
		model.addAttribute("vo", vo);
		return "modify";
	}
	
	@RequestMapping(value="/modify.do", method=RequestMethod.POST)
	public String modifyBoardOk(BoardVO vo){
		
//		System.out.println(vo.getSno());
//		System.out.println(vo.getTitle());
//		System.out.println(vo.getWriter());
//		System.out.println(vo.getBody());
		
		int result = repository.update(vo);
		if(result > 0) {
			return "redirect:/board/post.do?sno=" + vo.getSno();
		}else {
			return "redirect:/board/board.do";
		}
		
	}
	
	@RequestMapping(value="/delete.do", method=RequestMethod.POST)
	public String deleteBoard(@RequestParam("sno") int sno) {
		
		int result = repository.delete(sno);
		if(result > 0) {
			return "redirect:/board/board.do";
		}else {
			return "redirect:/board/post.do?sno=" + sno;
		}
	}
	
	public String getFileExtension(String fileName) {
		
		int index = fileName.lastIndexOf(".");
		if(index == -1) {
			return "";
		}
		return fileName.substring(index + 1);
		
	}
	
	
	
}



