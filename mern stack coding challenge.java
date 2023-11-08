<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    private Long id;
    private LocalDate dateOfSale;
    private String productName;
    private double transactionAmount;

    // Constructors, getters, and setters
}

spring.datasource.url=jdbc:postgresql://your-database-host:5432/your-database
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.datasource.driverClassName=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto = update

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DatabaseInitializationService {

    private final String THIRD_PARTY_API_URL = "https://s3.amazonaws.com/roxiler.com/product_transaction.json";

    public void initializeDatabase() {
        RestTemplate restTemplate = new RestTemplate();
        Transaction[] transactions = restTemplate.getForObject(THIRD_PARTY_API_URL, Transaction[].class);

        // Save transactions to the database
        // Implement the logic to save transactions to the database
    }
}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private DatabaseInitializationService databaseInitializationService;

    @GetMapping("/initialize-database")
    public String initializeDatabase() {
        databaseInitializationService.initializeDatabase();
        return "Database initialized successfully";
    }

    @GetMapping("/transactions-by-month")
    public List<Transaction> getTransactionsByMonth(@RequestParam String month) {
        // Implement the logic to retrieve transactions by month
    }
}
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    private Date date;

    // Getters and setters
}
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    private Date date;

    // Getters and setters
}
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    Page<Transaction> findByTitleContainingOrDescriptionContainingOrPrice(String title, String description, double price, Pageable pageable);
}
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public Page<Transaction> listTransactions(
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, perPage);

        if (searchText == null || searchText.isEmpty()) {
            return transactionRepository.findAll(pageRequest);
        }

        return transactionRepository.findByTitleContainingOrDescriptionContainingOrPrice(
                searchText, searchText, Double.parseDouble(searchText), pageRequest);
    }
}
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public double getTotalSaleAmountOfMonth(String month) {
        // Implement logic to calculate the total sale amount for the specified month
    }

    public long getTotalSoldItemsOfMonth(String month) {
        // Implement logic to calculate the
    	@RestController
    	@RequestMapping("/api/statistics")
    	public class StatisticsController {

    	    @Autowired
    	    private ProductTransactionRepository repository;

    	    @GetMapping("/total-sale-amount")
    	    public double getTotalSaleAmount(@RequestParam String month) {
    	        // Implement the logic to calculate the total sale amount for the selected month
    	    }

    	    @GetMapping("/total-sold-items")
    	    public long getTotalSoldItems(@RequestParam String month) {
    	        // Implement the logic to calculate the total number of sold items for the selected month
    	    }

    	    @GetMapping("/total-not-sold-items")
    	    public long getTotalNotSoldItems(@RequestParam String month) {
    	        // Implement the logic to calculate the total number of not sold items for the selected month
    	    }
    	}
    	@RestController
    	@RequestMapping("/api/barchart")
    	public class BarChartController {

    	    @Autowired
    	    private ProductTransactionRepository repository;

    	    @GetMapping("/price-range")
    	    public Map<String, Long> getPriceRangeCounts(@RequestParam String month) {
    	        // Implement the logic to calculate the number of items in each price range
    	        // based on the selected month, regardless of the year.
    	        // You may use a custom query to group and count items within each price range.
    	    }
    	}
    	@RestController
    	@RequestMapping("/api/piechart")
    	public class PieChartController {

    	    @Autowired
    	    private ProductTransactionRepository repository;

    	    @GetMapping("/unique-categories")
    	    public Map<String, Long> getUniqueCategories(@RequestParam String month) {
    	        // Implement the logic to find unique categories and the number of items
    	        // from each category for the selected month, regardless of the year.
    	        // You can use a custom query or Java logic to achieve this.
    	    }
    	}
    	public class BarChartResponse {
    	    // Define fields for the bar chart data
    	}

    	public class PieChartResponse {
    	    // Define fields for the pie chart data
    	}

    	public class CombinedResponse {
    	    private BarChartResponse barChartResponse;
    	    private PieChartResponse pieChartResponse;
    	    private List<ProductTransaction> productTransactions;

    	    // Constructors, getters, and setters
    	}
    	@RestController
    	@RequestMapping("/api/combined")
    	public class CombinedController {

    	    @Autowired
    	    private BarChartService barChartService;
    	    
    	    @Autowired
    	    private PieChartService pieChartService;
    	    
    	    @Autowired
    	    private ProductTransactionService productTransactionService;

    	    @GetMapping("/data")
    	    public CombinedResponse getCombinedData(@RequestParam String month) {
    	        BarChartResponse barChartResponse = barChartService.getPriceRangeCounts(month);
    	        PieChartResponse pieChartResponse = pieChartService.getUniqueCategories(month);
    	        List<ProductTransaction> productTransactions = productTransactionService.getTransactionsByMonth(month);

    	        CombinedResponse combinedResponse = new CombinedResponse(barChartResponse, pieChartResponse, productTransactions);
    	        
    	        return combinedResponse;
    	    }
    	}
