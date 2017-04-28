package org.sing_group.rnaseq.aibench.operations;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.BallgownWorkingDirectory;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;

@Operation(
	name = "View Ballgown working directory",
	description = "Views a Ballgown working directory."
)
public class ViewBallgownWorkingDirectory {
	private File directory;

	@Port(
		direction = Direction.INPUT,
		name = "Directory",
		description = "Directory that contains the Ballgown results.",
		allowNull = false,
		order = 1,
		extras="selectionMode=directories"
	)
	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Port(
		direction = Direction.OUTPUT,
		order = 2
	)
	public BallgownWorkingDirectory getBallgownWorkingDirectory() {
		return new BallgownWorkingDirectory(directory);
	}
}
