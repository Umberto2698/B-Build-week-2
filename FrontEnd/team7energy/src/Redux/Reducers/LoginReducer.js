const initialState = {
  validationContent: {
    email: "",
    password: "",
  },
  respLogin: {
    id: "",
    authorizationToken: "",
  },
};

const LoginReducer = (state = initialState, action) => {
  switch (action.type) {
    default:
      return state;
  }
};
export default LoginReducer;
