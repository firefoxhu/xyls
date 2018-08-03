/**
 * 
 */
package com.xyls.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author zhailiang
 *
 */
@NoRepositoryBean
public interface XylsRepository<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

}
