package co.edu.colomboamericano.caelstudent.service.utils;

public enum PrivacyPolicyState
{
	ACCEPTED("ONE"),
	REJECTED("TWO");

    private final String privacyPolicyState;

    PrivacyPolicyState(final String privacyPolicyState) {
        this.privacyPolicyState = privacyPolicyState;
    }

    @Override
    public String toString() {
        return privacyPolicyState;
    }
}
