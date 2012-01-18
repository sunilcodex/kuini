package pl.edu.uj.tcs.kuini;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IState;

public interface IController {
    IState getCurrentState();
    void proxyCommand(Command command);
}
