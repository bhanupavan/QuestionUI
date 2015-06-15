package com.mobolt.question.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.mobolt.mercury.dtos.core.QuestionDTO;

public class ExtractQuestions {

  public static List<QuestionDTO> data = Lists.newLinkedList();

  public static List<QuestionDTO> extract() {
	  data.clear();
    QuestionDTO q1 = new QuestionDTO();
    q1.setName("First Name");
    q1.setKey("first_name_key");
    q1.setLabel("First Name");

    QuestionDTO q2 = new QuestionDTO();
    q2.setName("Middle Name");
    q2.setKey("Middle_name_key");
    q2.setLabel("Middle Name");

    QuestionDTO q3 = new QuestionDTO();
    q3.setName("Last Name");
    q3.setKey("last_name_key");
    q3.setLabel("Last Name");

    data.add(q1);
    data.add(q2);
    data.add(q3);
    //StaticRam.saveQuestions(data);
    return data;

  }

}
