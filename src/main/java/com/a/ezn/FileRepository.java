package com.a.ezn;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepository {

	
	@Autowired
	SqlSessionTemplate template;
	
	public int insert(List<FileVO> vo) {
		return template.insert("FileMapper.insert", vo);
	}
}
