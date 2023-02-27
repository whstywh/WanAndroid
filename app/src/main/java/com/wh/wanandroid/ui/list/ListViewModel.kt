
class ListViewModel : BaseViewModel() {

    private val listLiveDataM = MutableLiveData<HomeDataFeed>()
    val listLiveData: LiveData<HomeDataFeed>
        get() = listLiveDataM


    private val homeRepository by lazy { HomeRepository() }

    fun getHomeList() {
        viewModelScope.launch {
            val homeDataFeed = homeRepository.getHomeList(1)
            if (homeDataFeed is NetResult.Success) {
                listLiveDataM.postValue(homeDataFeed.data)
            } else if (homeDataFeed is NetResult.Error) {
                Toast.makeText(App.instance(), homeDataFeed.exception.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}