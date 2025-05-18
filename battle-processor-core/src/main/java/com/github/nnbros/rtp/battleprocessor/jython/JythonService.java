package com.github.nnbros.rtp.battleprocessor.jython;

import com.github.nnbros.rtp.battleprocessor.configuration.BattleProcessorProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class JythonService {
	private final PythonInterpreter interpreter = new PythonInterpreter();
	private final BattleProcessorProperties properties;

	@PostConstruct
	private void init() {
		init(properties.getScriptsPath(), properties.getMainScriptName(), this.interpreter);
	}

	@PreDestroy
	private void close() {
		interpreter.close();
	}

	private static void init(Path scriptsPath, String mainScriptName, PythonInterpreter interpreter) {
		PySystemState sys = interpreter.getSystemState();
		sys.path.append(new PyString(scriptsPath.toString()));
		interpreter.execfile(scriptsPath.resolve(mainScriptName).toString());
	}

	@SuppressWarnings("unchecked")
	public <Response> Response call(@NonNull String functionName, @NonNull PyObject request, @NonNull Class<Response> responseType) {
		try {
			PyObject result = interpreter.get(functionName).__call__(request);
			return (Response) result.__tojava__(responseType);
		} catch (PyException e) {
			log.error("Failed to call Py function {}, Py traceback:\n{}", functionName, e.traceback.dumpStack());
			throw e;
		}
	}
}
