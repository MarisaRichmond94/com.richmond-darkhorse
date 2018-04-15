package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.gui.scene.LoadingScene;

public class LoadingSceneProgressUpdater implements Runnable{

	public LoadingScene scene;
	public float duration;
	
	public LoadingSceneProgressUpdater(LoadingScene scene,float duration) {
		this.scene = scene;
		this.duration = duration;
	}

	@Override
	/**
	 * Implements a pseudo loading bar based on the duration. The smaller the number, the quicker the load. The higher the number, the slower the load.
	 */
	public void run() {
		long startNanoTime = System.nanoTime();
		long nanoTime = 0;
		double progress = 0.0;
		while(progress < 1.0) {
			nanoTime = System.nanoTime() - startNanoTime;
			double secondsPassed = nanoTime /1000000000.0;
			progress = secondsPassed/duration;
			scene.setProgressBarValue(progress);
		}
		scene.setProgressBarValue(1);
	}
	
}
