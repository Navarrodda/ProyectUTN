package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.project.domain.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

}
