package com.example.hci.service;

import com.example.hci.controller.dto.CounselorBookCancelDTO;
import com.example.hci.controller.dto.CounselorBookDTO;
import com.example.hci.controller.dto.CounselorBookListDTO;
import com.example.hci.controller.dto.CounselorModifyDTO;
import com.example.hci.dao.dto.CounselorBook;

import java.util.List;

public interface ICounselorBookService extends BaseService<CounselorBook> {

    List<CounselorBook> getCounselorBookList(CounselorBookListDTO input);

    void book(CounselorBookDTO input);

    void modify(CounselorModifyDTO input);

    void addFellow(Integer userId, Integer counselorBookId, Integer fellowId);

    void deleteFellow(Integer userId, Integer counselorBookId, Integer fellowId);

    void cancel(CounselorBookCancelDTO input);

    List<String> date(String type);
}
