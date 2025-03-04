package hello.springmvc.jdbc.connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;

class DBConnectionTest {

  @Test
  void connection() {
    Connection connection = DBConnectionUtil.getConnection();
    assertThat(connection).isNotNull();
  }
}
