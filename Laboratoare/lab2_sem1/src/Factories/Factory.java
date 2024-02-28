package Factories;

import Containers.Container;
import Strategies.Strategy;

public interface Factory {
    Container createContainer(Strategy strategy);
}
