package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class MainApp {

   public static void main(String[] args) throws SQLException {

      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      addUsersWOCars(userService);
      getUsersWOCars(userService);

      User user5 = new User("User5", "Lastname4", "user4@mail.ru");
      user5.setCar(new Car("BMW", 555));
      userService.add(user5);

      getUsersWCars(context);
      context.close();
   }

   private static void addUsersWOCars(UserService userService) {
      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));
   }

   private static void getUsersWOCars(UserService userService) {
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println();
      }
   }

   private static void getUsersWCars(AnnotationConfigApplicationContext context) {
      TypedQuery<Car> query = context.getBean(SessionFactory.class).openSession().createQuery("from Car");
      List<Car> resultList = query.getResultList();
      context.close();
      for (Car car : resultList) {
         System.out.println("Id = " + car.getUser().getId());
         System.out.println("First Name = " + car.getUser().getFirstName());
         System.out.println("Last Name = " + car.getUser().getLastName());
         System.out.println("Email = " + car.getUser().getEmail());
         System.out.println("Car model = " + car.getModel());
         System.out.println("Car series = " +car.getSeries());
         System.out.println();
      }

   }
}
