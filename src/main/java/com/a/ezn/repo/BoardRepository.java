package com.a.ezn.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.a.ezn.BoardVO;

@Repository
public class BoardRepository {
	
	@Autowired
	JdbcTemplate template;
	
	/*
	 * root-context.xml에 bean으로 등록한 SqlSessionTemplate을
	 * IOC컨테이너에서 꺼내온다.
	 * SqlSessionTemplate은 mybatis를 사용하기 위해 설정해 놓은 것이다. 
	 */
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	private final String NAME_SPACE = "BoardMapper";
	
	public Page<BoardVO> getAllDate(Pageable pageable, String searchType, String keyword) {
		
		//데이터 조회할 때 시작하는 번호
		//pageable.getOffset();
		//전체 몇 페이지 나오는지 계삭해줌
		//pageable.getPageSize();
		Map<String,Object> map = new HashMap<String, Object>();
		//1 - 1페이지 , 10개씩 
		//0페이지, 10개씩
		//select * from samplTB 0, 10;
		
		map.put("offset", pageable.getOffset());
		map.put("limit", pageable.getPageSize());
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		int total = count(searchType, keyword);
		List<BoardVO> boards = sqlSessionTemplate.selectList(NAME_SPACE + ".getAll", map);
		return new PageImpl<>(boards, pageable, total);
		
	}
	
	public int count(String searchType, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		return sqlSessionTemplate.selectOne(NAME_SPACE + ".count", map);
	}
	
	public BoardVO selectOne(int sno) {
		
		return sqlSessionTemplate.selectOne(NAME_SPACE + ".findById", sno);
		
	}
	/*
	 * sqlSessionTemplate이 가지고 있는 insert 메서드를 이용해
	 * mybatis mapper를 찾아, BoardVO 타입의 파라미터를 넘겨준다.
	 * 아래 메서드는 mapper, xml중에 namespace가 "BoardMapper"이고,
	 * id가 "insert"인 xml을 찾아 파라미터로 BoardVO를 넘겨준다. 
	 */
	public int insertOne(BoardVO vo) {
		return sqlSessionTemplate.insert(NAME_SPACE + ".insert", vo);
	}
	
	public int update(BoardVO vo) {
		return sqlSessionTemplate.update(NAME_SPACE + ".update", vo);
	}
	public int delete(int sno) {
		return sqlSessionTemplate.delete(NAME_SPACE + ".delete", sno);
	}
	
}
