package hikaruna.optivisticlocking;

public class GuardData {

	private volatile long currentVersion;

	public synchronized <Result> void update(Updater<Result> updater, Result result) {
		try {
			if (currentVersion != updater.version) {
				throw new UpdateFailureException();
			}
			try {
				updater.update(result);
			} finally {
				currentVersion++;
			}
			updater.onPostSuccess();
		} catch (UpdateFailureException e) {
			updater.onPostFailure();
		} finally {
			updater.onFinaly();
		}
	}

	public abstract class Updater<Param> {

		private long version;

		public Updater() {
			version = currentVersion;
		}

		protected abstract void update(Param param);

		public void onPostSuccess() {
		};

		public void onPostFailure() {
		};

		public void onFinaly() {
		};
	}
}