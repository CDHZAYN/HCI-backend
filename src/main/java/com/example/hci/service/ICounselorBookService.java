package com.example.hci.service;

import com.example.hci.controller.dto.*;
import com.example.hci.dao.dto.CounselorBook;

import javax.xml.crypto.Data;
import java.util.List;

public interface ICounselorBookService extends BaseService<CounselorBook> {

    List<CounselorBook> getCounselorBookList(CounselorBookListDTO input);

    void book(CounselorBookDTO input);

    void modify(CounselorModifyDTO input);

    void addFellow(FellowModifyDTO input);

    void deleteFellow(FellowModifyDTO input);

    void cancel(CounselorBookCancelDTO input);

    List<String> date(DateDTO input);

    void finishUserCounselor();
}
