package org.leruk.spring.springmvcbooklibrary.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class BookDAO {
}
