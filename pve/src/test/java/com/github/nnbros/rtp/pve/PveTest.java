package com.github.nnbros.rtp.pve;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class PveTest {
	private AutoCloseable closeableMockContext;

	@BeforeEach
	public void openMocks() {
		closeableMockContext = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	public void releaseMocks() throws Exception {
		closeableMockContext.close();
	}
}
