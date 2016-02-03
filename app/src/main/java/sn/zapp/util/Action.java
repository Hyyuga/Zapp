package sn.zapp.util;

public enum Action{
    SHOW, CREATE, EDIT, DELETE, EDITCREATE, ADD, RESET, MINUS;

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(ordinal());
//    }
//
//    public static final Creator<Action> CREATOR = new Creator<Action>() {
//        @Override
//        public Action createFromParcel(final Parcel source) {
//            return Action.values()[source.readInt()];
//        }
//
//        @Override
//        public Action[] newArray(final int size) {
//            return new Action[size];
//        }
//    };
}
