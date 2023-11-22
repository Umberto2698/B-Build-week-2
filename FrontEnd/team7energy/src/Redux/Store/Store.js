import { combineReducers, configureStore } from "@reduxjs/toolkit";
import LoginReducer from "../Reducers/LoginReducer";

const rootReducer = combineReducers({
  login: LoginReducer,
});

const store = configureStore({
  reducer: rootReducer,
});
export default store;
