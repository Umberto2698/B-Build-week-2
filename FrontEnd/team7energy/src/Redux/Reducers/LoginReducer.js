import { EMAIL_VALIDATION, PASSWORD_VALIDATION, SAVE_TOKEN } from "../Actions/LoginActions";

const initialState = {
  validationContent: {
    email: "",
    password: "",
  },
  respLogin: {
    authorizationToken: "",
  },
};

const LoginReducer = (state = initialState, action) => {
  switch (action.type) {
    case EMAIL_VALIDATION:
      return {
        ...state,
        validationContent: {
          ...state.validationContent,
          email: action.payload,
        },
      };
    case PASSWORD_VALIDATION:
      return {
        ...state,
        validationContent: {
          ...state.validationContent,
          password: action.payload,
        },
      };
    case SAVE_TOKEN:
      return {
        ...state,
        ...state.validationContent,
        respLogin: {
          authorizationToken: action.payload,
        },
      };
    default:
      return state;
  }
};
export default LoginReducer;
