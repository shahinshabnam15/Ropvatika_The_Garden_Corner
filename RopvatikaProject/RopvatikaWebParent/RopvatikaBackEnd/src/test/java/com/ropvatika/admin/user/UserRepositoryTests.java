package com.ropvatika.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.mysql.cj.callback.UsernameCallback;
import com.ropvatika.common.entity.Role;
import com.ropvatika.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
@Autowired
private UserRepository repo;

@Autowired
private TestEntityManager entityManager;

@Test
public void testCreateNewUserWithOneRole() {
	Role roleAdmin = entityManager.find(Role.class, 1);
	User userNamHM = new User("nam@codejava.net","nam2020","Nam", "Ha Minh");
	userNamHM.addRole(roleAdmin);
	
	User savedUser = repo.save(userNamHM);
	
	assertThat(savedUser.getId()).isGreaterThan(0);
}

@Test
public void testCreateNewUserWithTwoRoles() {
	User userShabnam = new User("shabnam@gmail.com","shab2020","Shabnam","Shahin");
	Role roleEditor = new Role(4);
	Role roleAssistant = new Role(6);
	
	userShabnam.addRole(roleEditor);
	userShabnam.addRole(roleAssistant);
	
	User savedUser = repo.save(userShabnam);
	
	assertThat(savedUser.getId()).isGreaterThan(0);
}

@Test
public void testListAllUsers()
{
	Iterable<User> listUsers = repo.findAll();
	listUsers.forEach(user -> System.out.println(user));
	
}

@Test
public void testGetUserById()
{
	User userNam = repo.findById(1).get();
	System.out.println(userNam);
	assertThat(userNam).isNotNull();
	
}

@Test
public void testUpdateUserDetails()
{
	User userNam = repo.findById(1).get();
	userNam.setEnabled(true);
	userNam.setEmail("namjavaprogrammer@gmail.com");
	
	repo.save(userNam);
}

@Test
public void testUpdateUserRoles()
{
	User userShabnam = repo.findById(2).get();
	Role roleEditor = new Role(4);
	Role roleSalesperson = new Role(3);
	
	 userShabnam .getRoles().remove(roleEditor);
	 userShabnam.addRole(roleSalesperson);
	 
	 repo.save(userShabnam);
}

@Test
public void testDeleteUser()
{
	Integer userId = 2;
	repo.deleteById(userId);
	
	
}

@Test
public void testGetUserByEmail()
{
	String email = "shabnam@yahoo.com";
	User user = repo.getUserByEmail(email);
	assertThat(user).isNotNull();
	}

@Test
public void testCountById()
{
	Integer id = 3;
	Long countById =repo.countById(id);
	
	assertThat(countById).isNotNull().isGreaterThan(0);
}


@Test
public void testDisableUser()
{
	Integer id = 36;
	repo.updateEnabledStatus(id,false);
	
}

@Test
public void testEnableUser()
{
	Integer id = 39;
	repo.updateEnabledStatus(id,true);
	
}

@Test
public void testListFirstPage() {
	int pageNumber = 0;
	int pageSize = 4;
	
	Pageable pageable = PageRequest.of(pageNumber, pageSize);
	Page<User> page = repo.findAll(pageable);
	
	List<User> listUsers = page.getContent();
	
	listUsers.forEach(user -> System.out.println(user));
	
	assertThat(listUsers.size()).isEqualTo(pageSize);
}

@Test
public void testSearchUsers() {
	String keyword = "bruce";
	
	int pageNumber = 0;
	int pageSize = 4;
	
	Pageable pageable = PageRequest.of(pageNumber, pageSize);
	Page<User> page = repo.findAll(keyword, pageable);
	
	List<User> listUsers = page.getContent();
	
	listUsers.forEach(user -> System.out.println(user));
	
	assertThat(listUsers.size()).isGreaterThan(0);
	
}


}

