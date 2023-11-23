export const EMAIL_VALIDATION = "EMAIL_VALIDATION";
export const PASSWORD_VALIDATION = "PASSWORD_VALIDATION";
export const SAVE_TOKEN = "SAVE_TOKEN";

export const fetchLogin = (stateEmail, statePassword) => {
  return async (dispatch) => {
    try {
      const resp = await fetch("http://localhost:3002/auth/login", {
        method: "POST",
        body: JSON.stringify({
          email: stateEmail,
          password: statePassword,
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (resp.ok) {
        const token = await resp.json();
        dispatch({ type: SAVE_TOKEN, payload: token });
      }
    } catch (err) {
      console.log(err);
    }
  };
};
