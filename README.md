ridill is a project to produce a stub.
���̃v���W�F�N�g(ridill)�̓X�^�u��񋟂���v���W�F�N�g�ł��B

1. introduction 
�͂��߂�

It is cumbersome to write code that returns stuffed with value in many of the stub.
�����̃X�^�u�ɒl���l�ߕԂ��R�[�h���������Ƃ͖ʓ|�ł��B

For example (Java):
�Ⴆ�� (Java) �ł�

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
���̂悤��API�����b�v�����C���^�[�t�F�[�X�̃X�^�u�������K�v������܂��B

And, The more API and services, is cumbersome to write the code for the stub.
�����āAAPI��T�[�r�X�������Ȃ�΂Ȃ�قǁA�X�^�u�̃R�[�h�������̂��ʓ|�ł��B

Therefore, as one of the answers in this project (ridill), 
it will provide a stub to be automatically generated using the reflection.
�̂ɁA���̃v���W�F�N�g�ł͉񓚂�1�Ƃ��āA���t���N�V�������g���������������s���X�^�u��񋟂��܂��B



2. usage
�g����

StubFactory factory = new StubFactory();
UserAPIProvidedByACompany api = factory.create(UserAPIProvidedByACompany.class);
//Set the value in auto
//�����Œl���ݒ肳��܂�
List<User> frends = api.getFrends(userId);


More details on How to use it, look at the following.
���g�����́A�ȉ������Ă��������B

src/ridill/src/test/java/org/synthe/ridill/scenario/testcase/ScenarioStubFactoryTest


