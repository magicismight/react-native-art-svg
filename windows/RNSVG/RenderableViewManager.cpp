#include "pch.h"
#include "RenderableViewManager.h"
#if __has_include("RenderableViewManager.g.cpp")
#include "RenderableViewManager.g.cpp"
#endif

using namespace winrt;
using namespace Microsoft::ReactNative;

namespace winrt::RNSVG::implementation
{
  Windows::UI::Xaml::FrameworkElement RenderableViewManager::CreateView()
  {
    switch (m_class)
    {
      case RNSVG::SVGClass::RNSVGGroup: return winrt ::RNSVG::GroupView(m_reactContext);
      case RNSVG::SVGClass::RNSVGRect: return winrt::RNSVG::RectView(); 
    }

    throw hresult_not_implemented();
  }

  // IViewManagerWithNativeProperties
  IMapView<hstring, ViewManagerPropertyType> RenderableViewManager::NativeProps()
  {
    auto nativeProps = winrt::single_threaded_map<hstring, ViewManagerPropertyType>();

    nativeProps.Insert(L"fill", ViewManagerPropertyType::Number);
    nativeProps.Insert(L"stroke", ViewManagerPropertyType::Number);
    nativeProps.Insert(L"strokeWidth", ViewManagerPropertyType::Number);

    return nativeProps.GetView();
  }

  void RenderableViewManager::UpdateProperties(
      Windows::UI::Xaml::FrameworkElement const &view,
      Microsoft::ReactNative::IJSValueReader const &propertyMapReader)
  {
    if (auto renderable{view.try_as<RenderableView>()})
    {
      renderable->UpdateProperties(propertyMapReader);
    }
  }
} // namespace winrt::RNSVG::implementation
