@import exercise.dto.posts.PostsPage
@param PostsPage page

@template.layout.page(
    page = page,
    content = @`
        <h1>Посты</h1>
        <table class="table table-striped">
            @for(var post : page.getPosts())
                <tr>
                    <td>
                        ${post.getId()}
                    </td>
                    <td>
                        <a href="/posts/${post.getId()}">${post.getName()}</a>
                    </td>
                </tr>
            @endfor
        </table>
    `
)

