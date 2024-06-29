package br.com.renatoganske.email_template_management_ms.repositories;

import br.com.renatoganske.email_template_management_ms.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {
}
