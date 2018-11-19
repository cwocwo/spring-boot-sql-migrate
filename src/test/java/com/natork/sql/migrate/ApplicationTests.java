package com.natork.sql.migrate;

import java.net.ConnectException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.core.NestedCheckedException;
import org.springframework.test.context.junit4.SpringRunner;

import com.natork.sql.migrate.Application;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testDefaultSettings() throws Exception {
		try {
			Application.main(new String[] { "--server.port=0" });
		}
		catch (IllegalStateException ex) {
			if (serverNotRunning(ex)) {
				return;
			}
		}
		String output = this.outputCapture.toString();
		System.out.println("=========================================================================================");
		System.out.println(output);
		System.out.println("=========================================================================================");
		assertThat(output).contains("Successfully acquired change log lock")
				.contains("Creating database history table with name: public.databasechangelog")
				.contains("Table person created")
				.contains("ChangeSet db/changelog/rds/mysql/db.changelog-create-person-table.yaml::1::marceloverdijk ran successfully")
				
				.contains("New row inserted into person")
				.contains("ChangeSet db/changelog/rds/mysql/db.changelog-insert-person-data.yaml::2::marceloverdijk ran successfully")
				.contains("Successfully released change log lock");
	}

	@SuppressWarnings("serial")
	private boolean serverNotRunning(IllegalStateException ex) {
		NestedCheckedException nested = new NestedCheckedException("failed", ex) {
		};
		if (nested.contains(ConnectException.class)) {
			Throwable root = nested.getRootCause();
			if (root.getMessage().contains("Connection refused")) {
				return true;
			}
		}
		return false;
	}

}
