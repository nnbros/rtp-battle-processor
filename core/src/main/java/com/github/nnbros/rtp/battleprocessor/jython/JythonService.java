package com.github.nnbros.rtp.battleprocessor.jython;

import com.github.nnbros.rtp.battleprocessor.configuration.BattleProcessorProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class JythonService {
	private final PythonInterpreter interpreter = new PythonInterpreter();
	private final BattleProcessorProperties properties;

	@PostConstruct
	private void init() {
		init(properties.getMainScriptPath(), this.interpreter);
	}

	@PreDestroy
	private void close() {
		interpreter.close();
	}

	private static void init(Path script, PythonInterpreter interpreter) {
		interpreter.execfile(script.toAbsolutePath().toString());
	}

	@SuppressWarnings("unchecked")
	public <Response> Response call(@NonNull String functionName, @NonNull PyObject request, @NonNull Class<Response> responseType) {
		PyObject result = interpreter.get(functionName).__call__(request);
		return (Response) result.__tojava__(responseType);
	}
}
