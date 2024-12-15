import { createSlice } from "@reduxjs/toolkit";
import { PayloadAction } from "@reduxjs/toolkit/dist/createAction";
import { RootState } from "./store";
import Cookies from "js-cookie";

export type AuthStore = {
  username: string | null;
};

const initialState = {
  username: null,
} as AuthStore;

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setUser(state, action: PayloadAction<AuthStore>) {
      state.username = action.payload.username;
    },
    logoutUser(state) {
      state.username = null;
      Cookies.remove("token");
    },
  },
});

export const { setUser, logoutUser } = authSlice.actions;
export default authSlice.reducer;

export const selectUser = (state: RootState) => state.username;
