package br.com.renatoganske.email_template_management_ms.repositories;

import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipientRepository extends JpaRepository<Recipient, UUID> {
}
