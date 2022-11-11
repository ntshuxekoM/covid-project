package com.covid.analysis.app.repository.entities;

import com.covid.analysis.app.model.entities.EmailContent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailContentRepository extends JpaRepository<EmailContent, Long> {

}