ridill is a project to produce a stub.
このプロジェクト(ridill)はスタブを提供するプロジェクトです。

1. introduction 
はじめに

It is cumbersome to write code that returns stuffed with value in many of the stub.
多くのスタブに値を詰め返すコードを書くことは面倒です。

For example (Java):
例えば (Java) では

public interface UserAPIProvidedByACompany{
    public User getById(String loginId);
    public Boolean login(String loginId, String passwd);
    public List<User> getFrendsById(Long userId);
}

public class UserAPIProvidedByACompanyStub implements UserAPIProvidedByACompany{
    @Override
    public User getByUser(String loginId){
        User user = new User();
        user.setFirstName("firstname");
        user.setLastName("lastname");
        .
        .
        .
        return user;
    }
    @Override
    public List<User> getFrendsById(Long userId){
        List<User> frends = new ArrayList<>();
        for(int i = 0; i < 40; i++){
            User user = new User();
            user.setFirstName("firstname"+i);
            user.setLastName("lastname"+i);
            .
            .
            .
            frends.add(user);
        }
        return frends;
    }
}

There is a need to write a stub of the interface that wraps such API.
このようなAPIをラップしたインターフェースのスタブを書く必要があります。

And, The more API and services, is cumbersome to write the code for the stub.
そして、APIやサービスが多くなればなるほど、スタブのコードを書くのが面倒です。

Therefore, as one of the answers in this project (ridill), 
it will provide a stub to be automatically generated using the reflection.
故に、このプロジェクトでは回答の1つとして、リフレクションを使った自動生成を行うスタブを提供します。



2. usage
使い方

StubFactory factory = new StubFactory();
UserAPIProvidedByACompany api = factory.create(UserAPIProvidedByACompany.class);
//Set the value in auto
//自動で値が設定されます
List<User> frends = api.getFrends(userId);


More details on How to use it, look at the following.
より使い方は、以下を見てください。

src/ridill/src/test/java/org/synthe/ridill/scenario/testcase/ScenarioStubFactoryTest


