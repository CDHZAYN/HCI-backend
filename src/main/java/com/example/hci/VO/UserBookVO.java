package com.example.hci.VO;

import com.example.hci.dao.dto.Book;
import com.example.hci.dao.dto.UserBook;
import lombok.Data;

import java.util.List;

@Data
public class UserBookVO {

    private Book book;

    private UserBook userBook;

    private List<FellowBriefVO> userFellow;
}
